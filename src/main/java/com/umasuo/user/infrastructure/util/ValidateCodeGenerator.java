package com.umasuo.user.infrastructure.util;

import java.util.Random;

/**
 * The type Token generator.
 */
public final class ValidateCodeGenerator {

  /**
   * Validate code length.
   */
  private static final int LENGTH = 4;

  /**
   * Instantiates a new Validate code generator.
   */
  private ValidateCodeGenerator() {
  }

  /**
   * Generate string.
   *
   * @return the string
   */
  public static String generate() {
    StringBuilder code = new StringBuilder();
    Random r = new Random();

    for (int i = 0; i < LENGTH; i++) {
      int t = r.nextInt(10);
      code.append(t);
    }

    return code.toString();
  }
}
