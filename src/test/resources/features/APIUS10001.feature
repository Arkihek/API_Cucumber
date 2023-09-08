@US1001
Feature: api/opdList

  Scenario: api/opdList endpoint`ine gecerli authorization bilgileri ile bir GET request gonderildiginde donen status code`un 200 oldugu ve response message bilgisinin "Success" oldugu dogrulanmali
    Given API kullanici sisteme admin olarak giris yapar
    And API kullanicisi url ile birlikte iki parametreli giris yapar parametreler "api" ve "opdList"
    When API kullanici bodysiz get request yapar
    Then API kullanici istenen bilgileri test eder