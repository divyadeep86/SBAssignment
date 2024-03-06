package com.example.sbtakehomeassignment

import com.example.sbtakehomeassignment.userInfo.data.UserApiTest
import com.example.sbtakehomeassignment.userInfo.data.UserRepositoryTest
import com.example.sbtakehomeassignment.userInfo.domain.GetUserReposUseCaseTest
import com.example.sbtakehomeassignment.userInfo.domain.UserInfoUseCaseTest
import com.example.sbtakehomeassignment.userInfo.ui.UserInfoViewModelTest
import com.example.sbtakehomeassignment.userInfo.ui.UserReposViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses


@RunWith(Suite::class)
@SuiteClasses(
    UserApiTest::class,
    UserRepositoryTest::class,
    UserInfoUseCaseTest::class,
    GetUserReposUseCaseTest::class,
    UserInfoViewModelTest::class,
    UserReposViewModelTest::class
)
class AllTestsSuite {
}
