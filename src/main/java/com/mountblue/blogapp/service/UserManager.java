package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.RoleDao;
import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.Role;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.security.WebUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManager implements UserService{
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserManager(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(WebUser webUser){
        User user = new User();
        user.setUsername(webUser.getUsername());
        user.setEmail(webUser.getEmail());
        user.setName(webUser.getName());
        user.setEnabled(true);
        user.setRoles(Collections.singletonList(roleDao.findRoleByName("ROLE_WEBUSER")));
        user.setPassword(bCryptPasswordEncoder.encode(webUser.getPassword()));
        userDao.save(user);
    }
    @Override
    public User findUserByName(String userName) {
        return userDao.findByName(userName);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public int findUserIdByName(String name) {
        User user = findUserByName(name);
        return user.getId();
    }

    public List<User> findUserList(List<Integer> ids){
        List<User> users = new ArrayList<>();
        for(int id : ids){
            users.add(findUserById(id));
        }
        return users;
    }

    public List<User> findUsersLike(String userPattern){
        return userDao.findUserByNameLike(userPattern);
    }

    @Override
    public List<User> findUsersByNames(List<String> userNames) {
        List<User> users = new ArrayList<>();
        for(String userName : userNames){
            User user = findUserByName(userName);
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> findAllUsers(){
        return userDao.findAll();
    }

    @Override
    public Optional<User> findUserByUserName(String username){
        return userDao.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userDao.findUserByUsername(username);
        if(maybeUser.isEmpty()){
            throw new UsernameNotFoundException("no such username exist");
        }
        User user = maybeUser.get();
        System.out.println("#####" + user.getRoles());
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            System.out.println(tempRole.getName());
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }
}
