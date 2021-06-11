package net.jaggerwang.sbip.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.RoleBO;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import net.jaggerwang.sbip.usecase.port.dao.RoleDao;
import net.jaggerwang.sbip.usecase.port.dao.UserDao;
import net.jaggerwang.sbip.util.encoder.PasswordEncoder;
import net.jaggerwang.sbip.util.generator.RandomGenerator;
import org.springframework.stereotype.Component;

/**
 * @author Jagger Wang
 */
@Component
public class UserUsecase {
    private final static HashMap<String, String> MOBILE_VERIFY_CODES = new HashMap<>();
    private final static HashMap<String, String> EMAIL_VERIFY_CODES = new HashMap<>();

    private final UserDao userDAO;
    private final RoleDao roleDAO;

    public UserUsecase(UserDao userDAO, RoleDao roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    public UserBO register(UserBO userBO) {
        if (userDAO.findByUsername(userBO.getUsername()).isPresent()) {
            throw new UsecaseException("用户名重复");
        }

        var user = UserBO.builder().username(userBO.getUsername())
                .password(encodePassword(userBO.getPassword())).build();
        return userDAO.save(user);
    }

    public String encodePassword(String password) {
        return new PasswordEncoder().encode(password);
    }

    public UserBO modify(Long id, UserBO userBO) {
        var user = userDAO.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("用户未找到");
        }

        if (userBO.getUsername() != null) {
            if (userDAO.findByUsername(userBO.getUsername()).isPresent()) {
                throw new UsecaseException("用户名重复");
            }
            user.setUsername(userBO.getUsername());
        }
        if (userBO.getPassword() != null) {
            user.setPassword(encodePassword(userBO.getPassword()));
        }
        if (userBO.getMobile() != null) {
            if (userDAO.findByMobile(userBO.getMobile()).isPresent()) {
                throw new UsecaseException("手机重复");
            }
            user.setMobile(userBO.getMobile());
        }
        if (userBO.getEmail() != null) {
            if (userDAO.findByEmail(userBO.getEmail()).isPresent()) {
                throw new UsecaseException("邮箱重复");
            }
            user.setEmail(userBO.getEmail());
        }
        if (userBO.getAvatarId() != null) {
            user.setAvatarId(userBO.getAvatarId());
        }
        if (userBO.getIntro() != null) {
            user.setIntro(userBO.getIntro());
        }

        return userDAO.save(user);
    }

    public String sendMobileVerifyCode(String type, String mobile) {
        var key = String.format("%s_%s", type, mobile);
        if (MOBILE_VERIFY_CODES.get(key) == null) {
            var code = new RandomGenerator().numberString(6);
            MOBILE_VERIFY_CODES.put(key, code);
        }
        return MOBILE_VERIFY_CODES.get(key);
    }

    public Boolean checkMobileVerifyCode(String type, String mobile, String code) {
        var key = String.format("%s_%s", type, mobile);
        if (code != null && code.equals(MOBILE_VERIFY_CODES.get(key))) {
            MOBILE_VERIFY_CODES.remove(key);
            return true;
        } else {
            return false;
        }
    }

    public String sendEmailVerifyCode(String type, String email) {
        var key = String.format("%s_%s", type, email);
        if (EMAIL_VERIFY_CODES.get(key) == null) {
            var code = new RandomGenerator().numberString(6);
            EMAIL_VERIFY_CODES.put(key, code);
        }
        return EMAIL_VERIFY_CODES.get(key);
    }

    public Boolean checkEmailVerifyCode(String type, String email, String code) {
        var key = String.format("%s_%s", type, email);
        if (code != null && code.equals(EMAIL_VERIFY_CODES.get(key))) {
            EMAIL_VERIFY_CODES.remove(key);
            return true;
        } else {
            return false;
        }
    }

    public Optional<UserBO> info(Long id) {
        return userDAO.findById(id);
    }

    public Optional<UserBO> infoByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public Optional<UserBO> infoByMobile(String mobile) {
        return userDAO.findByMobile(mobile);
    }

    public Optional<UserBO> infoByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public List<RoleBO> roles(String username) {
        return roleDAO.rolesOfUser(username);
    }

    public void follow(Long followerId, Long followingId) {
        userDAO.follow(followerId, followingId);
    }

    public void unfollow(Long followerId, Long followingId) {
        userDAO.unfollow(followerId, followingId);
    }

    public Boolean isFollowing(Long followerId, Long followingId) {
        return userDAO.isFollowing(followerId, followingId);
    }

    public List<UserBO> following(Long followerId, Long limit, Long offset) {
        return userDAO.following(followerId, limit, offset);
    }

    public Long followingCount(Long followerId) {
        return userDAO.followingCount(followerId);
    }

    public List<UserBO> follower(Long followingId, Long limit, Long offset) {
        return userDAO.follower(followingId, limit, offset);
    }

    public Long followerCount(Long followingId) {
        return userDAO.followerCount(followingId);
    }
}
