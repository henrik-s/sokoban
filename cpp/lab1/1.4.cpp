/* Generated file, do not edit */

#ifndef CXXTEST_RUNNING
#define CXXTEST_RUNNING
#endif

#define _CXXTEST_HAVE_STD
#define _CXXTEST_HAVE_EH
#include <cxxtest/TestListener.h>
#include <cxxtest/TestTracker.h>
#include <cxxtest/TestRunner.h>
#include <cxxtest/RealDescriptions.h>
#include <cxxtest/ErrorPrinter.h>

int main() {
 return CxxTest::ErrorPrinter().run();
}
#include "test_vec.cpp"

static MyTestSuite suite_MyTestSuite;

static CxxTest::List Tests_MyTestSuite = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_MyTestSuite( "test_vec.cpp", 4, "MyTestSuite", suite_MyTestSuite, Tests_MyTestSuite );

static class TestDescription_MyTestSuite_test_assign_vector : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_assign_vector() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 8, "test_assign_vector" ) {}
 void runTest() { suite_MyTestSuite.test_assign_vector(); }
} testDescription_MyTestSuite_test_assign_vector;

static class TestDescription_MyTestSuite_test_copy_vector : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_copy_vector() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 24, "test_copy_vector" ) {}
 void runTest() { suite_MyTestSuite.test_copy_vector(); }
} testDescription_MyTestSuite_test_copy_vector;

static class TestDescription_MyTestSuite_test_rand : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_rand() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 34, "test_rand" ) {}
 void runTest() { suite_MyTestSuite.test_rand(); }
} testDescription_MyTestSuite_test_rand;

static class TestDescription_MyTestSuite_test_initialize_with_scalar : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_initialize_with_scalar() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 41, "test_initialize_with_scalar" ) {}
 void runTest() { suite_MyTestSuite.test_initialize_with_scalar(); }
} testDescription_MyTestSuite_test_initialize_with_scalar;

static class TestDescription_MyTestSuite_test_index_insertion : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_index_insertion() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 50, "test_index_insertion" ) {}
 void runTest() { suite_MyTestSuite.test_index_insertion(); }
} testDescription_MyTestSuite_test_index_insertion;

#include <cxxtest/Root.cpp>
