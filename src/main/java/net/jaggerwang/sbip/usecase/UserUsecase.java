package net.jaggerwang.sbip.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.RoleEntity;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import net.jaggerwang.sbip.usecase.port.encoder.PasswordEncoder;
import net.jaggerwang.sbip.usecase.port.generator.RandomGenerator;
import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;
import net.jaggerwang.sbip.usecase.port.repository.UserRepository;

public class UserUsecase {
    private final static HashMap<String, String> mobileVerifyCodes = new HashMap<>();
    private final static HashMap<String, String> emailVerifyCodes = new HashMap<>();

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RandomGenerator randomGenerator;
    private final PasswordEncoder passwordEncoder;

    public UserUsecase(UserRepository userRepository, RoleRepository roleRepository,
                       RandomGenerator randomGenerator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.randomGenerator = randomGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity register(UserEntity userEntity) {
        if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            throw new UsecaseException("用户名重复");
        }

        var user = UserEntity.builder().username(userEntity.getUsername())
                .password(encodePassword(userEntity.getPassword())).build();
        return userRepository.save(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserEntity modify(Long id, UserEntity userEntity) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("用户未找到");
        }

        if (userEntity.getUsername() != null) {
            if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
                throw new UsecaseException("用户名重复");
            }
            user.setUsername(userEntity.getUsername());
        }
        if (userEntity.getPassword() != null) {
            user.setPassword(encodePassword(userEntity.getPassword()));
        }
        if (userEntity.getMobile() != null) {
            if (userRepository.findByMobile(userEntity.getMobile()).isPresent()) {
                throw new UsecaseException("手机重复");
            }
            user.setMobile(userEntity.getMobile());
        }
        if (userEntity.getEmail() != null) {
            if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
                throw new UsecaseException("邮箱重复");
            }
            user.setEmail(userEntity.getEmail());
        }
        if (userEntity.getAvatarId() != null) {
            user.setAvatarId(userEntity.getAvatarId());
        }
        if (userEntity.getIntro() != null) {
            user.setIntro(userEntity.getIntro());
        }

        return userRepository.save(user);
    }

    public String sendMobileVerifyCode(String type, String mobile) {
        var key = String.format("%s_%s", type, mobile);
        if (mobileVerifyCodes.get(key) == null) {
            var code = randomGenerator.numberString(6);
            mobileVerifyCodes.put(key, code);
        }
        return mobileVerifyCodes.get(key);
    }

    public Boolean checkMobileVerifyCode(String type, String mobile, String code) {
        var key = String.format("%s_%s", type, mobile);
        if (code != null && code.equals(mobileVerifyCodes.get(key))) {
            mobileVerifyCodes.remove(key);
            return true;
        } else {
            return false;
        }
    }

    public String sendEmailVerifyCode(String type, String email) {
        var key = String.format("%s_%s", type, email);
        if (emailVerifyCodes.get(key) == null) {
            var code = randomGenerator.numberString(6);
            emailVerifyCodes.put(key, code);
        }
        return emailVerifyCodes.get(key);
    }

    public Boolean checkEmailVerifyCode(String type, String email, String code) {
        var key = String.format("%s_%s", type, email);
        if (code != null && code.equals(emailVerifyCodes.get(key))) {
            emailVerifyCodes.remove(key);
            return true;
        } else {
            return false;
        }
    }

    public Optional<UserEntity> info(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> infoByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> infoByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    public Optional<UserEntity> infoByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<RoleEntity> roles(String username) {
        return roleRepository.rolesOfUser(username);
    }

    public void follow(Long followerId, Long followingId) {
        userRepository.follow(followerId, followingId);
    }

    public void unfollow(Long followerId, Long followingId) {
        userRepository.unfollow(followerId, followingId);
    }

    public Boolean isFollowing(Long followerId, Long followingId) {
        return userRepository.isFollowing(followerId, followingId);
    }

    public List<UserEntity> following(Long followerId, Long limit, Long offset) {
        return userRepository.following(followerId, limit, offset);
    }

    public Long followingCount(Long followerId) {
        return userRepository.followingCount(followerId);
    }

    public List<UserEntity> follower(Long followingId, Long limit, Long offset) {
        return userRepository.follower(followingId, limit, offset);
    }

    public Long followerCount(Long followingId) {
        return userRepository.followerCount(followingId);
    }
}
