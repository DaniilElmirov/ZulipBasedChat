package com.elmirov.course.util.rule

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import com.elmirov.course.R
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class FragmentTestRule(
    private val fragment: Fragment,
    private val fragmentArguments: Bundle = bundleOf()
): TestRule {

    val wiremockRule = WireMockRule()

    override fun apply(base: Statement?, description: Description?): Statement {
        FragmentScenario.launchInContainer(fragment::class.java, fragmentArguments, R.style.Base_Theme_Homework_1)

        return RuleChain.emptyRuleChain()
            .around(wiremockRule)
            .apply(base, description)
    }
}