package com.example.sbtakehomeassignment

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    UserInfoScreenUiStateTest::class,
    UserRepoDetailsScreenUiStateTest::class,
)
class TestSuites {
}