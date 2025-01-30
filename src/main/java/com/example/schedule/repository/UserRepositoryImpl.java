package com.example.schedule.repository;

import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        if (StringUtils.hasText(user.getEmail())) {
            params.put("email", user.getEmail());
        }
        LocalDateTime now = LocalDateTime.now();
        params.put("reg_date", now);
        params.put("mod_date", now);
        params.put("is_deleted", 0);

        Number id = insert.executeAndReturnKey(params);
        return new UserResponseDto(id.longValue(), user.getName(), user.getEmail(), now, now);
    }

    @Override
    public User findUserById(Long id) {
        List<User> result = jdbcTemplate.query("SELECT * FROM user WHERE id = ? AND is_deleted = 0", userRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, String.valueOf(id)));
    }

    @Override
    public List<UserResponseDto> findAllUser() {
        return jdbcTemplate.query("select * from user where is_deleted = 0", userResponseRowMapper());
    }

    @Override
    public int updateUser(Long id, User user) {
        String sql = "UPDATE user SET name = ?, email = ? WHERE is_deleted = 0 AND id = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), id);
    }

    @Override
    public int deleteUser(Long id) {
        return jdbcTemplate.update("UPDATE user SET is_deleted = 1 WHERE id = ?", id);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getBoolean("is_deleted"),
                rs.getTimestamp("reg_date").toLocalDateTime(),
                rs.getTimestamp("mod_date").toLocalDateTime()
        );
    }

    private RowMapper<UserResponseDto> userResponseRowMapper() {
        return (rs, rowNum) -> new UserResponseDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("reg_date").toLocalDateTime(),
                rs.getTimestamp("mod_date").toLocalDateTime()
        );
    }
}
