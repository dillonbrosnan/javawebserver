package Date;

import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;

public class FormattedDate {
  private LocalDateTime date;
  private static DateTimeFormatter RFC_1123 = (     
    DateTimeFormatter.RFC_1123_DATE_TIME
  );
  
  public FormattedDate( LocalDateTime date ) {
    this.date = date;
  }
  
  public FormattedDate( long milliseconds ) {
    Instant instant = Instant.ofEpochMilli( milliseconds );
    ZonedDateTime zonedDateTime = instant.atZone( ZoneId.of ( "GMT" ));
    date = zonedDateTime.toLocalDateTime();
  }
  
  public String toString() {
    return ZonedDateTime.of( this.date, ZoneId.of( "GMT" ) ).format( RFC_1123 );
  }
}