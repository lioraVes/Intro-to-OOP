import java.util.Scanner;

class Chat {
    public static void main(String[] args) {

        ChatterBot[] chatterBots_arr={null,null};
        ChatterBot first_bot= new ChatterBot("first bot",
                new String[]{"say " +ChatterBot.REQUESTED_PHRASE_PLACEHOLDER,
                        "say "+ChatterBot.REQUESTED_PHRASE_PLACEHOLDER+
                        "? okay: "+ChatterBot.REQUESTED_PHRASE_PLACEHOLDER}
                ,new String[]{"what ", "dude what do you mean? ","ha? ", "no sorry cant say that ","no "});
        chatterBots_arr[0]=first_bot;
        ChatterBot second_bot= new ChatterBot("second bot",
                new String[]{ChatterBot.REQUESTED_PHRASE_PLACEHOLDER,"okay here goes: "+ChatterBot.REQUESTED_PHRASE_PLACEHOLDER}
                ,new String[]{"Sorry I don't understand ", "what? ", "try again ", "can you understand me? ","don't talk to me like that! "});
        chatterBots_arr[1]=second_bot;

        String statement="say hi!";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            for(ChatterBot bot : chatterBots_arr) {
                statement = bot.replyTo(statement);
                System.out.print(bot.getName()+ ": "+ statement);
                scanner.nextLine();
            }
        }


    }
}
