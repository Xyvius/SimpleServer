/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.message;

import static simpleserver.lang.Translations.t;
import static simpleserver.util.Util.*;

import java.util.IllegalFormatException;

import simpleserver.Player;
import simpleserver.Server;
import simpleserver.config.xml.Group;

public class GlobalChat extends AbstractChat {

  private static final String GLOBAL_CHAT = t("global");

  public GlobalChat(Player sender) {
    super(sender);
    chatRoom = GLOBAL_CHAT;
  }

  @Override
  public String buildMessage(String message) {
    return getPrefix() + message;
  }

  @Override
  protected boolean sendToPlayer(Player reciever) {
    return true;
  }

  private String getPrefix() {
    Server server = sender.getServer();

    String prefix = "";
    char color = 'f';
    String title = "";
    String format = server.config.properties.get("msgFormat");
    Group group = sender.getGroup();

    if (group != null) {
      color = group.color;
      if (group.showTitle) {
        title = group.name;
        format = server.config.properties.get("msgTitleFormat");
      }
    }

    try {
      prefix = String.format(format, sender.getName(), title, color);
    } catch (IllegalFormatException e) {
      println("There is an error in your msgFormat/msgTitleFormat settings!");
    }
    return prefix;
  }

  @Override
  public void noRecieverFound() {
    return;
  }

}
