package org.gradle.scrooge

import groovy.io.FileType
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ScroogePluginTest extends Specification {

    Project project = ProjectBuilder.builder().build()
    File thriftDirectory = new File("src/main/thrift/")
    File destinationDirectory = new File("src/gen/scala")

    def setup() {
        project.apply plugin: 'scrooge-gradle'
    }

    def cleanup(){
        destinationDirectory.delete()
    }

    def "compileScrooge is available"() {
        expect:
        project.tasks.compileScrooge instanceof ScroogeCompileTask
    }

    def "compile builds 2 scala files"() {
        given:
        ScroogeCompileTask compileScrooge = project.tasks.compileScrooge
        compileScrooge.dest = destinationDirectory
        compileScrooge.thriftFiles = thriftDirectory.listFiles().toList()

        when:
        compileScrooge.compile()

        then: "There should be two scala files generated"
        def count = 0
        destinationDirectory.traverse(
                type: FileType.FILES,
                nameFilter: ~/.*\.scala/){ File file ->
            count++
        }
        count == 2
    }

    def "compile builds 2 scala files with finagle options set"() {
        given:
        ScroogeCompileTask compileScrooge = project.tasks.compileScrooge
        compileScrooge.dest = destinationDirectory
        compileScrooge.thriftFiles = thriftDirectory.listFiles().toList()
        compileScrooge.opts = ["--finagle"]

        when:
        compileScrooge.compile()

        then: "There should be two scala files generated"
        def count = 0
        destinationDirectory.traverse(
                type: FileType.FILES,
                nameFilter: ~/.*\.scala/){ File file ->
            count++
        }
        count == 2
    }
}
