using System;

namespace SpeedCarRacing
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("SPEED CAR RACING");
            Race race = new Race();
            race.StartRacing();
        }
    }
}