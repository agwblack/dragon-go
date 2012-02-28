package com.agwblack.DragonGo;

public class DGSEnumType {

  public static enum Command {
    LOGIN,
    STATUS,
    DOWNLOAD_GAME,
    QUICK_PLAY,
    QUICK_STATUS
  }

  public static enum Error {
    CANNOT_FIND_SERVER,
    BAD_LOGIN_DETAILS,
    NONE
  }
}
