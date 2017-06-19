package com.umasuo.user.infrastructure.util;

import com.umasuo.authentication.Scope;
import com.umasuo.authentication.Token;
import com.umasuo.authentication.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/19.
 */
public class TokenUtil {

  public static Token generateToken(TokenType tokenType, String subjectId, long expires,
                                    List<Scope> scopes) {
    Token token = new Token();
    token.setTokenType(tokenType);
    token.setSubjectId(subjectId);
    token.setExpiresIn(expires);
    token.setGenerateTime(System.currentTimeMillis());
    if (scopes != null) {
      token.setScopes(scopes);
    } else {
      token.setScopes(new ArrayList<>());
    }

    return token;
  }
}
