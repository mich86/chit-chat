package android.example.chitchat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

// put the messages in date order
public class ChatComparator implements Comparator<ChatMessage> {

    public int compare(ChatMessage left, ChatMessage right) {

        try {

            //compare
            Date leftDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).parse(left.getChatMessageSendTime());
            Date rightDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).parse(right.getChatMessageSendTime());

            if (leftDate.before(rightDate))
                return 1;
            else if (leftDate.after(rightDate))
                return -1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
