using System;

namespace SpeedCarRacing
{
    public abstract class AbstractCar
    {
        public enum CarColor { RED, BLUE, YELLOW, BLACK, WHITE, GREEN, PINK };
        public enum CarStatus { HEALTHY, BOOST, BROKEN };

        public float Speed { get; set; } // en km/h
        public float Distance { get; set; }
        public CarStatus Status { get; set; }
        public CarColor Color { get; }
        public float SpecialProb { get; set; }

        public AbstractCar()
        {
            Speed = 0;
            Distance = 0;
            Color = CarColor.RED;
            SpecialProb = 0;
            Status = CarStatus.HEALTHY;
        }
        
        public AbstractCar(CarColor color) : this()
        {
            Color = color;
        }

        public static AbstractCar Build(int carIndex, int colorIndex)
        {
            if (colorIndex >= Enum.GetNames(typeof(CarColor)).Length)
            {
                throw new ArgumentException("Car color is undefined");
            }
            CarColor color = (CarColor) colorIndex;
            switch (carIndex)
            {
                case 0:
                    return new FastCar(color);
                case 1:
                    return new SlowCar(color);
                default:
                    throw new ArgumentException("Car type is undefined");
            }
        }

        private bool CanMove()
        {
            return Status != CarStatus.BROKEN;
        }

        public void Move()
        {
            if (CanMove())
            {
                Distance += ConvertSpeedToKmPerSecond() * Config.CYCLE_TIME_S;
            }
        }

        private float ConvertSpeedToKmPerSecond()
        {
            return Speed / 3600;
        }

        public bool IsRunning()
        {
            return !IsFinished();
        }

        private bool IsFinished()
        {
            return Distance >= Config.MAX_DISTANCE_KM;
        }
        
        public void EnableSpecial()
        {
            BeforeSpecial();
            if (CanEnableSpecial())
            {
                Special();
            }
        }
        
        private bool CanEnableSpecial()
        {
            Random random = new Random();
            return random.NextDouble() <= SpecialProb;
        }

        protected abstract void BeforeSpecial();

        public abstract void Special(); 
        
        public abstract void UpdateCycle(); 

        public override string ToString()
        {
            return GetType().Name + " [" + Color + " | " + Status + " | " + Speed + " km/h]: "
                + Distance + " km";
        }

    }
}
