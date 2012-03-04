package com.agwblack.DragonGo;

import java.lang.Exception;
import java.io.IOException;

import  org.apache.http.cookie.Cookie;

public class Message {
  /** The HttpClient instance*/
  ApacheHttpClient client = new ApacheHttpClient();
  
  /** The Base address for the server */
  private String address = new String("http://www.dragongoserver.net/");
  
  /** A string which holds the response to the message */
  private String response;

  /**
   * \brief Creates a new message to send to the server.
   * The size of the args parameter may determine what kind of message gets
   * sent.
   *
   * \param command - specifies the type of message we wish to send
   * \param args - gives additional arguments to the message
   */
  Message(DGSEnumType.Command command, String[] args) {
    switch (command) {
      case QUICK_STATUS:
        address += "quick_status.php";
        if (args.length == 1) {
          address += "?user=" + args[0];
        }
        break;
      case LOGIN:
        address += "login.php?quick_mode=1&userid=" + args[0] + "&passwd="
          + args[1];
        break;
      case DOWNLOAD_GAME:
        // need to decide how to deal with owned_comments parameter
        address += "sgf.php?gid=" + args[0];
        address += "&owned_comments=1&quick_mode=1";
        break;
      case QUICK_PLAY:
        address += "quick_play.php?gid=" + args[0] + "&color=" 
          + args[1] + "sgf_prev=" + args[2] + "&board_move=" + args[3];
        if (args.length == 5) {
          address += "&message=" + args[4];
        }
        break;
      default:
    }
  }

  /**
   * Sends the message to the server
   */
  public DGSEnumType.Error send() {
    DGSEnumType.Error error = DGSEnumType.Error.NONE;
    System.out.println("Sending http request to " + address);
    error = client.sendMessage(address);
    if (error == DGSEnumType.Error.NONE) {
      response = client.getMessageResponse();
      return error;
    } 
    return error;
  }

  /**
   * Returns the response from the server
   */
  public String getResponse() {
    return response;
  }

  /**
   * Returns the cookies downloaded from the messages
   * FIXME: We should really make this less dependent on the Apache Cookie class -
   * easier once we know precisely what we need to store
   */
  public Cookie[] getCookies() {
    return client.getCookies();
  }

  /**
   * Adds Cookies to the message
   * FIXME: Hopefully we can load these automatically from the database
   * depending on message type, instead of adding the manually by calling this
   * command
   */
  public void setCookies(Cookie ck) {
    client.addCookie(ck);
  }

}
