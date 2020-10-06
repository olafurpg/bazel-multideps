package tests

import multideps.Multideps

import moped.testkit.MopedSuite

abstract class BaseSuite extends MopedSuite(Multideps.app) {}
