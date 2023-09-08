@US1002
Feature: posts/70

  Scenario: posts/70 endpoint`ine gecerli authorization bilgileri ile bir PUT request gonderildiginde donen response'in response body'sinin asagida verilen ile ayni oldugunu test ediniz

    Given API kullanicisi url ile birlikte iki parametreli giris yapar parametreler "posts" ve 70
    When API kullanici request body ile put request yapar
    And Excepted Data olusturulur
    Then Excepted Data ile response ayni mi dogrulanir