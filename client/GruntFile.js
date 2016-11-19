module.exports  = function(grunt) {

    grunt.initConfig({
        run : {
            options: {

            },
            react: {
                cmd: "npm",
                args: [
                    'run',
                    'build'
                ]
            }
        },

        copy: {
            static: {
                files: [
                    { expand: true, cwd: 'build', src: ['**'], dest: './../src/main/resources/static/'}
                ]
            }
        },

        clean: {
            static_build: {
                options: {
                    force: true
                },
                src: ['./../src/main/resources/static/*/**', './../src/main/resources/static/*']
            }

        }
    });

    grunt.loadNpmTasks('grunt-run');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');

    grunt.registerTask('build', ['run:react','clean:static_build','copy:static']);
    grunt.registerTask('default', 'build');
};