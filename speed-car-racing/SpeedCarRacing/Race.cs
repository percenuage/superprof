using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.Threading;

namespace SpeedCarRacing
{
    public class Race
    {
        enum CyclePhase { IDLE, RUN, SPECIAL, WAIT }

        private AbstractCar Car { get; set; }
        private CyclePhase Phase { get; set; }
        private Stopwatch Watch { get; }

        public Race()
        {
            Phase = CyclePhase.IDLE;
            Watch = new Stopwatch();
        }

        public void StartRacing()
        {
            Init();
            do
            {
                Run();
                Specials();
                Wait();
            } while (IsRunning());
            Stop();
        }
        
        private void Init()
        {
            Console.WriteLine("# Select a car:");
            Console.WriteLine("[0] Fast Car");
            Console.WriteLine("[1] Slow Car");
            string carUserInput = Console.ReadLine();
            Console.WriteLine("# Select a color:");
            foreach (AbstractCar.CarColor color in Enum.GetValues(typeof(AbstractCar.CarColor)))
            {
                Console.WriteLine("[{0}] {1}", (int)color, color);

            }
            string colorUserInput = Console.ReadLine();
            BuildCarFromInputs(carUserInput, colorUserInput);
            Console.WriteLine(this);
            Watch.Start();
        }
        
        private void BuildCarFromInputs(string carInput, string colorInput)
        {
            int carIndex = int.Parse(carInput);
            int colorIndex = int.Parse(colorInput);
            try
            {
                Car = AbstractCar.Build(carIndex, colorIndex);
            }
            catch (Exception e)
            {
                Console.WriteLine("/!\\ " + e.Message + " => Exit app /!\\");
                Environment.Exit(0);
            }

        }

        private void Run() {
            Phase = CyclePhase.RUN;
            Console.WriteLine("[{0}] Car is moving...", Phase);
            Car.Move();
        }
        
        private void Specials() {
            Phase = CyclePhase.SPECIAL;
            Console.WriteLine("[{0}] Car special is enabling...", Phase);
            Car.EnableSpecial();
        }

        private void Wait() {
            Phase = CyclePhase.WAIT;
            Console.WriteLine("[{0}] Car is spending time...", Phase);
            Thread.Sleep(Config.WAITING_TIME_S * 1000);
            Car.UpdateCycle();
            Console.WriteLine(this);
        }

        private void Stop() { 
            Phase = CyclePhase.IDLE;
            Console.WriteLine("[{0}] Car is stopping...", Phase);
            Watch.Stop();
            TimeSpan ts = Watch.Elapsed;
            Console.WriteLine("Race Time: " + $"{ts.Hours:00}:{ts.Minutes:00}:{ts.Seconds:00}.{ts.Milliseconds / 10:00}");
        }

        private bool IsRunning()
        {
            return Car.IsRunning();
        }
        
        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Cars:\n");
            sb.Append("- ").Append(Car).Append("\n");
            return sb.ToString();
        }
    }
}