package com.umasuo.user.infrastructure.exception;

/**
 * Not sign in exception.
 */
public class NotSignInException extends RuntimeException {

  /**
   * Default constructor.
   */
  public NotSignInException() {
    super();
  }

  /**
   * Constructor with message.
   *
   * @param msg
   */
  public NotSignInException(String msg) {
    super(msg);
  }

  /**
   * Constructor with message & cause.
   *
   * @param msg
   * @param e
   */
  public NotSignInException(String msg, Throwable e) {
    super(msg, e);
  }
}
