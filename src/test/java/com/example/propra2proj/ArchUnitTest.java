package com.example.propra2proj;


import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = Propra2projApplication.class, importOptions = {ImportOption.DoNotIncludeTests.class} )
public class ArchUnitTest {

    @ArchTest
    ArchRule allClassesInServiceShouldBeAnnotatedWithService= classes().that()
            .resideInAPackage("..applicationlayer.service..").should().beAnnotatedWith(Service.class);


    @ArchTest
    ArchRule allClassesInControllerShouldBeAnnotatedWithController= classes().that()
            .resideInAPackage("..controller..").should().beAnnotatedWith(Controller.class);


    @ArchTest
    ArchRule allClassesInReposImplShouldBeAnnotatedWithRepository= classes().that()
            .resideInAPackage("..infrastructurelayer.reposimplementation..").should().beAnnotatedWith(Repository.class);

    @ArchTest
    ArchRule allClassesInExamAggregateShouldNotBeDependOnAnyOtherAggregate= noClasses().that()
            .resideOutsideOfPackage("..domain.model.examagg").should().accessClassesThat().resideInAPackage("domain.model.examagg");

    @ArchTest
    ArchRule allClassesInUserAggregateShouldNotDependOnAnyOtherAggregate= noClasses().that().resideOutsideOfPackage("useragg")
            .should().accessClassesThat().resideInAPackage("useragg");


    @ArchTest
    ArchRule allClassesInApplicationLayerShouldOnlyBeAccessedByClassesInDomainLayer=
            classes().that()
            .resideInAPackage("..applicationlayer..").should()
                    .onlyBeAccessed().byClassesThat().resideInAPackage("..domain..");

    @ArchTest
    ArchRule allClassesInDomainShouldNotDependOnAnyOtherClass= classes().that().resideInAPackage("..domain..").should()
            .onlyDependOnClassesThat().resideInAPackage("..domain..");



    @ArchTest
    ArchRule OnionArchitecture= onionArchitecture().domainModels("..domain.model..")
            .domainServices("..domain.repository..")
            .applicationServices("..applicationlayer..")
            .adapter("web","controller")
            .adapter("config","config")
            .adapter("db","..infrastructurelayer..");

    


}
