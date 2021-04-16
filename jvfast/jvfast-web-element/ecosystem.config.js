module.exports = {
  apps: [
    {
      name: 'jvfast',
      cwd: './',
      script: './node_modules/nuxt-start/bin/nuxt-start.js',
      env: {
        HOST: '127.0.0.1',
        PORT: 3006,
        NODE_ENV: 'production'
      },
      watch: [
        'dist',
        '.dist',
        '_nuxt'
      ]
    }
  ]
}
