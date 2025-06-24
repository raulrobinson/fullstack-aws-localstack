module.exports = [
  {
    context: ['/api'],
    target: 'http://localhost:9800',
    secure: false,
    changeOrigin: true,
  },
];
