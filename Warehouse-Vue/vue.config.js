module.exports = {
  devServer: {
    // 注意，由于有下面的代理
    // 这里的host没有必要改成0.0.0.0，反正外面访问不到
    // 用nginx部署时再考虑不迟
    host: "localhost",
    port: 8080,
    https: false,
    open: true,

    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
