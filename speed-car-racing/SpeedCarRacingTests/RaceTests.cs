using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SpeedCarRacing.Tests
{
    [TestClass()]
    public class RaceTests
    {
        private Race race;
        private AbstractCar car;

        [TestInitialize()]
        public void Initialize() {
            race = new Race();
            car = new SlowCar(AbstractCar.CarColor.RED);
        }
    }
}