using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;

namespace SpeedCarRacing.Tests
{
    [TestClass()]
    public class AbstractCarTests
    {
        private const int CAR_SPEED = 100;
        
        private Car car;

        [TestInitialize()]
        public void Initialize() {
            car = new Car(AbstractCar.CarColor.BLUE);
            car.Speed = CAR_SPEED;
        }

        [TestMethod()]
        public void MoveTest()
        {
            Assert.AreEqual(0, car.Distance);
            car.Move();
            float distanceTraveled = (car.Speed / 3600) * Config.CYCLE_TIME_S;
            Assert.AreEqual(distanceTraveled, car.Distance);
        }

        [TestMethod()]
        [ExpectedException(typeof(NotImplementedException))]
        public void SpecialTest()
        {
            car.Special();
        }

        [TestMethod()]
        public void ToStringTest()
        {
            Assert.AreEqual("Car [BLUE | HEALTHY | 100 km/h]: 0 km", car.ToString());
        }
    }
}