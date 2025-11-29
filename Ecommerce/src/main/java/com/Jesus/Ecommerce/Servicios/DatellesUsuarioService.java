package com.Jesus.Ecommerce.Servicios;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface DatellesUsuarioService {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException ;

}
