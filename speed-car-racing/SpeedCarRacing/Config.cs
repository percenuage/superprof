namespace SpeedCarRacing
{
    public static class Config
    {
        // RACE
        public const int CYCLE_TIME_S = 60;
        public const int WAITING_TIME_S = 2;
        public const int MAX_DISTANCE_KM = 50;
        
        // ABSTRACTCAR
        
        // SLOWCAR
        public const int SLOW_CAR_SPEED = 50;
        public const float SLOW_CAR_SPECIAL_PROB = 0.3f;
        public const string BOOST_WORD = "VITESSE";
        public const int BOOST_SPEED = 120;
        public const int BOOST_CYCLE = 2;
        public const int BOOST_USER_INPUT_COUNTDOWN_S = 3;
        
        // FASTCAR
        public const int FAST_CAR_SPEED = 100;
        public const float FAST_CAR_SPECIAL_PROB = 0.6f;
    }
}