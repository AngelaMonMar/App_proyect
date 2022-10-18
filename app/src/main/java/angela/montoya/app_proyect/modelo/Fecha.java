package angela.montoya.app_proyect.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {

        static String findDifference(String start_date, String end_date){
            String salida="";

            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            // Try Block
            try {

                // parse method is used to parse
                // the text from a string to
                // produce the date
                Date d1 = sdf.parse(start_date);
                Date d2 = sdf.parse(end_date);

                // Calucalte time difference
                // in milliseconds
                long difference_In_Time
                        = d2.getTime() - d1.getTime();

                // Calucalte time difference in
                // seconds, minutes, hours, years,
                // and days
                long difference_In_Seconds
                        = (difference_In_Time
                        / 1000)
                        % 60;

                long difference_In_Minutes
                        = (difference_In_Time
                        / (1000 * 60))
                        % 60;

                long difference_In_Hours
                        = (difference_In_Time
                        / (1000 * 60 * 60))
                        % 24;

                long difference_In_Years
                        = (difference_In_Time
                        / (1000l * 60 * 60 * 24 * 365));

                long difference_In_Days
                        = (difference_In_Time
                        / (1000 * 60 * 60 * 24))
                        % 365;

                // Print the date difference in
                // years, in days, in hours, in
                // minutes, and in seconds

                System.out.print(
                        "Difference "
                                + "between two dates is: ");

                if(difference_In_Years>0){//+ de un año solo salen los años
                    salida=difference_In_Years+ " years, ";
                }
                else{
                    if(difference_In_Days>0){//+ de un dia solo salen los dias
                        salida=difference_In_Days+ " days, ";
                    }else{
                        if(difference_In_Hours>0){
                            salida= difference_In_Hours+ " hours, ";
                        }else{
                            if(difference_In_Minutes>0)
                                salida+= difference_In_Minutes+ " minutes, ";

//                     salida+= difference_In_Seconds
//                        + " seconds";
                        }
                    }
                }

            }
            catch ( ParseException e) {
                e.printStackTrace();
            }
            return salida;
        }

        // Driver Code
       /* public static void main(String[] args){
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String dateNow=df.format(new Date());
            // Given start Date
            String date_publicado
                    = "20-03-2022 19:10:20";

            // Function Call
            System.out.println(findDifference(date_publicado, dateNow));
        }*/
   }

