module.exports = {
  apps: [
    {
      name: 'doc',
      cwd: './',
      script: 'serve',
      env: {
        PM2_SERVE_PATH: 'dist',
        PM2_SERVE_PORT: 3007,
        NODE_ENV: 'production'
      },
      watch: [
        'dist',
        '.dist'
      ]
    }
  ]
}
