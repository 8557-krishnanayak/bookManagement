package com.godigit.bookmybook.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.godigit.bookmybook.dto.DataHolder;


public class TokenUtility {
    private final static String SECRET = "1234567890zxcvbnmasdfghjklqwertyuiop!@#$%^&*()";

    /**
     * Purpose: This method generates a JWT token for a given user ID and role.
     *
     * @param id The ID of the user for whom the token is being generated.
     * @param role The role of the user for whom the token is being generated.
     *
     * @return Returns a JWT token as a String.
     */
    public String getToken(Long id, String role) {

        return JWT.create()
                .withClaim("id", id)
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * Purpose: This method decodes a given JWT token to extract user details.
     *
     * @param token The JWT token to be decoded.
     *
     * @return Returns a DataHolder object containing the user ID and role extracted from the token.
     */
    public DataHolder decode(String token) {
        Long id = JWT
                .require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("id")
                .asLong();


        String role = JWT
                .require(Algorithm.HMAC256(SECRET))
                .build().verify(token).getClaim("role").asString();


        return DataHolder.builder().id(id).role(role).build();
    }

}


