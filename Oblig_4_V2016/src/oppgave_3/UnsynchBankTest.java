package oppgave_3;

//UnsynchBankTest.java

/** 
 This program shows data corruption 
 when multiple threads access a data structure
*/
public class UnsynchBankTest
{ 
	public static final int NACCOUNTS = 10;
	public static final int INITIAL_BALANCE = 10000;
	
	public static void main (String [] args)
  {  
	Banktest b = new Banktest (NACCOUNTS, INITIAL_BALANCE);
    int i;
    for (i = 0; i < NACCOUNTS; i++)
    {  
       TransferThread t = new TransferThread (b, i,
          INITIAL_BALANCE);
       t.setPriority (Thread.NORM_PRIORITY + i % 2);
       t.start ();
    }
  }
}

/**
 A bank with a number of bank accounts.
*/
class Banktest
{ 
 public static final int NTEST = 10000;
 private final int[] accounts;
 private long ntransacts = 0;
 
 /**
    Constructs the bank.
    @param n the number of accounts
    @param initialBalance the initial balance
    for each account
 */
 public Banktest (int n, int initialBalance)
 {   
    accounts = new int[n];
    int i;
    for (i = 0; i < accounts.length; i++)
       accounts[i] = initialBalance;
    ntransacts = 0;
 }

 /**
    Transfers money from one account to another.
    @param from the account to transfer from
    @param to the account to transfer to
    @param amount the amount to transfer
 */
 public void transfer (int from, int to, int amount)
    throws InterruptedException
 {  
    accounts[from] -= amount;
    accounts[to] += amount;
    ntransacts++;
    if (ntransacts % NTEST == 0) test ();
 }

 /**
    Prints a test message to check the integrity
    of this bank object.
 */
 public void test ()
 {  
    int sum = 0;

    for (int i = 0; i < accounts.length; i++)
       sum += accounts[i];

    System.out.println ("Transactions:" + ntransacts
       + " Sum: " + sum);
 }

 /**
    Gets the number of accounts in the bank.
    @return the number of accounts
 */
 public int size ()
 {  
    return accounts.length;
 }
}

/**
 A thread that transfers money from an account to other
 accounts in a bank.
*/
class TransferThread extends Thread
{  
	private Banktest bank;
	private int fromAccount;
	private int maxAmount;
	private static final int REPS = 1000;
	
	/**
    Constructs a transfer thread.
    @param b the bank between whose account money is transferred
    @param from the account to transfer money from
    @param max the maximum amount of money in each transfer 
 */
 public TransferThread (Banktest b, int from, int max)
 {  
    bank = b;
    fromAccount = from;
    maxAmount = max;
 }

 public void run ()
 {  
    try
    {  
       while (!interrupted ())
       {  
          for (int i = 0; i < REPS; i++)
          {
             int toAccount = (int)(bank.size () * Math.random ());
             int amount = (int)(maxAmount * Math.random () / REPS);
             bank.transfer (fromAccount, toAccount, amount);
             sleep (1);
          }
       }
    }
    catch (InterruptedException e) {}
 }


}


