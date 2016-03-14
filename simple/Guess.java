package simple;

class Guess {
	public static void main(String[] args) throws Exception
   {
      outer:
      while (true)
      {
         System.out.println("I'm thinking of a number between 0 and 9.");
         int number = (int) (Math.random() * 10);
         while (true)
         {
            int guessNumber;
            while (true)
            {
               System.out.println("Enter your guess number between 0 and 9.");
               guessNumber = System.in.read();
               while (System.in.read() != '\n');
               if (guessNumber >= '0' && guessNumber <= '9')
               {
                  guessNumber -= '0';
                  break;
               }
            }
            if (guessNumber < number)
               System.out.println("Your guess is too low.");
            else
            if (guessNumber > number)
               System.out.println("Your guess is too high.");
            else
            {
               System.out.println("Congratulations! You guessed correctly.");
               while (true)
               {
                  System.out.println("Press n for new game or q to quit.");
                  int ch = System.in.read();
                  while (System.in.read() != '\n');
                  if (ch == 'n')
                     continue outer;
                  if (ch == 'q')
                     break outer;
               }
            }
         }
      }
   }
}