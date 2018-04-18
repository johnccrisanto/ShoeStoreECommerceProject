package com.johncrisanto.shoestore.entity.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = -782753297050771940L;
	
	private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
