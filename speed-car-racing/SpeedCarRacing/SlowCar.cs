﻿using System;
using System.Collections.Generic;
 using System.Linq;
 using System.Text;
 using System.Threading;
 using System.Threading.Tasks;
 using System.Timers;
 using Timer = System.Timers.Timer;

 namespace SpeedCarRacing
{
    public class SlowCar : AbstractCar
    {
        private int BoostRemainingCycle { get; set; }

        public SlowCar(CarColor color) : base(color)
        {
            Speed = Config.SLOW_CAR_SPEED;
            SpecialProb = Config.SLOW_CAR_SPECIAL_PROB;
            BoostRemainingCycle = 0;
        }

        private void EnableBoost()
        {
            Status = CarStatus.BOOST;
            Speed = Config.BOOST_SPEED;
            BoostRemainingCycle = Config.BOOST_CYCLE;
        }

        private void DisableBoost()
        {
            Status = CarStatus.HEALTHY;
            Speed = Config.SLOW_CAR_SPEED;
        }

        protected override void BeforeSpecial() { }

        public override void Special()
        {
            if (SpecialUserInput())
            {
                EnableBoost();
            }
        }
        
        private bool SpecialUserInput()
        {
            string word = Shuffle(Config.BOOST_WORD);
            Console.WriteLine("# Enter '{0}' to enable boost:", word);
            string userInput = InputWithCountDown(Config.BOOST_USER_INPUT_COUNTDOWN_S);
            if (userInput != null && userInput.ToUpper() == word)
            {
                Console.WriteLine("# Boost enabled :)");
                return true;
            }
            Console.WriteLine("# You failed :(");
            return false;
        }
        
        private static string InputWithCountDown(int sec)
        {
            string input = null;
            int count = 1;
            while (true)
            {
                if (Console.KeyAvailable)
                {
                    input = Console.ReadLine();
                    if (input != null) break;
                }
                Thread.Sleep(1000);
                if (count++ > sec) break;
            }

            return input;
        }

        private string Shuffle(string str)
        {
            Random r = new Random();
            return new string(str.ToCharArray().OrderBy(s => (r.Next(2) % 2) == 0).ToArray());
        }
        
        public override void UpdateCycle()
        {
            if (Status != CarStatus.BOOST) return;
            BoostRemainingCycle--;
            if (BoostRemainingCycle < 0)
            {
                DisableBoost();
            }
        }
        
        private static class TimerUtil
        {
            public static void Start(int sec)
            {
                Timer timer = new Timer(sec * 1000);
                timer.Elapsed += TimerTick;
                timer.Start();

                Console.WriteLine("Press a key to continue.");
                Console.ReadKey();
                timer.Stop();
                Console.WriteLine("Stopped");
                Console.Read();
            }
            
            private static void TimerTick(Object obj, ElapsedEventArgs e)
            {
                Console.WriteLine("Exiting");
                Environment.Exit(0);
            }
        }

    }

}
