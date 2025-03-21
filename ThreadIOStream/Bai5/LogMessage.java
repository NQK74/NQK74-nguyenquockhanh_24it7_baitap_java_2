package ThreadIOStream.Bai5;
import java.util.Date;

class LogMessage {
   private final String message;
   private final Date timestamp;

   public LogMessage(String message) {
      this.message = message;
      this.timestamp = new Date();
   }

   public String getMessage() {
      return message;
   }

   public Date getTimestamp() {
      return timestamp;
   }
}

