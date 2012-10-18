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

static class TestDescription_MyTestSuite_test_assign_vector_string : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_assign_vector_string() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 24, "test_assign_vector_string" ) {}
 void runTest() { suite_MyTestSuite.test_assign_vector_string(); }
} testDescription_MyTestSuite_test_assign_vector_string;

static class TestDescription_MyTestSuite_test_copy_string_vector : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_copy_string_vector() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 37, "test_copy_string_vector" ) {}
 void runTest() { suite_MyTestSuite.test_copy_string_vector(); }
} testDescription_MyTestSuite_test_copy_string_vector;

static class TestDescription_MyTestSuite_test_copy_double_vector : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_copy_double_vector() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 46, "test_copy_double_vector" ) {}
 void runTest() { suite_MyTestSuite.test_copy_double_vector(); }
} testDescription_MyTestSuite_test_copy_double_vector;

static class TestDescription_MyTestSuite_test_double_rand : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_double_rand() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 55, "test_double_rand" ) {}
 void runTest() { suite_MyTestSuite.test_double_rand(); }
} testDescription_MyTestSuite_test_double_rand;

static class TestDescription_MyTestSuite_test_initialize_string_with_scalar : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_initialize_string_with_scalar() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 62, "test_initialize_string_with_scalar" ) {}
 void runTest() { suite_MyTestSuite.test_initialize_string_with_scalar(); }
} testDescription_MyTestSuite_test_initialize_string_with_scalar;

static class TestDescription_MyTestSuite_test_index_string_insertion : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_index_string_insertion() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 71, "test_index_string_insertion" ) {}
 void runTest() { suite_MyTestSuite.test_index_string_insertion(); }
} testDescription_MyTestSuite_test_index_string_insertion;

static class TestDescription_MyTestSuite_test_pushbacki_int : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_pushbacki_int() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 81, "test_pushbacki_int" ) {}
 void runTest() { suite_MyTestSuite.test_pushbacki_int(); }
} testDescription_MyTestSuite_test_pushbacki_int;

static class TestDescription_MyTestSuite_test_pushback_string : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_pushback_string() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 92, "test_pushback_string" ) {}
 void runTest() { suite_MyTestSuite.test_pushback_string(); }
} testDescription_MyTestSuite_test_pushback_string;

static class TestDescription_MyTestSuite_test_insert : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_insert() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 104, "test_insert" ) {}
 void runTest() { suite_MyTestSuite.test_insert(); }
} testDescription_MyTestSuite_test_insert;

static class TestDescription_MyTestSuite_test_insert_rand : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_insert_rand() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 121, "test_insert_rand" ) {}
 void runTest() { suite_MyTestSuite.test_insert_rand(); }
} testDescription_MyTestSuite_test_insert_rand;

static class TestDescription_MyTestSuite_test_erase : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_erase() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 129, "test_erase" ) {}
 void runTest() { suite_MyTestSuite.test_erase(); }
} testDescription_MyTestSuite_test_erase;

static class TestDescription_MyTestSuite_test_clear : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_clear() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 140, "test_clear" ) {}
 void runTest() { suite_MyTestSuite.test_clear(); }
} testDescription_MyTestSuite_test_clear;

static class TestDescription_MyTestSuite_test_size : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_size() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 148, "test_size" ) {}
 void runTest() { suite_MyTestSuite.test_size(); }
} testDescription_MyTestSuite_test_size;

static class TestDescription_MyTestSuite_test_sort_int : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_sort_int() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 156, "test_sort_int" ) {}
 void runTest() { suite_MyTestSuite.test_sort_int(); }
} testDescription_MyTestSuite_test_sort_int;

static class TestDescription_MyTestSuite_test_sort_string : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_sort_string() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 169, "test_sort_string" ) {}
 void runTest() { suite_MyTestSuite.test_sort_string(); }
} testDescription_MyTestSuite_test_sort_string;

static class TestDescription_MyTestSuite_test_exists : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_exists() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 179, "test_exists" ) {}
 void runTest() { suite_MyTestSuite.test_exists(); }
} testDescription_MyTestSuite_test_exists;

static class TestDescription_MyTestSuite_test_pointerVector : public CxxTest::RealTestDescription {
public:
 TestDescription_MyTestSuite_test_pointerVector() : CxxTest::RealTestDescription( Tests_MyTestSuite, suiteDescription_MyTestSuite, 190, "test_pointerVector" ) {}
 void runTest() { suite_MyTestSuite.test_pointerVector(); }
} testDescription_MyTestSuite_test_pointerVector;

#include <cxxtest/Root.cpp>
