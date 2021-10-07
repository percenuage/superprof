﻿using System;

namespace SpeedCarRacing.Tests
{
    class Car : AbstractCar
    {
        public Car(CarColor color) : base(color)
        {
        }

        protected override void BeforeSpecial()
        {
            throw new NotImplementedException();
        }

        public override void Special()
        {
            throw new NotImplementedException();
        }

        public override void UpdateCycle()
        {
            throw new NotImplementedException();
        }
    }
}
