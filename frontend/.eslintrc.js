module.exports = {
    root: true,
    env: {
      node: true,
      browser: true
    },
    extends: [
      'plugin:vue/recommended', // Para Vue 2
      'eslint:recommended'
    ],
    parserOptions: {
      parser: 'babel-eslint'
    },
    rules: {
      // Regras específicas para resolver os erros atuais
      'vue/valid-v-slot': ['error', { allowModifiers: true }],
      'vue/no-parsing-error': [2, { 'x-invalid-end-tag': false }],
      
      // Regras adicionais recomendadas
      'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'vue/multi-word-component-names': 'off' // Desativa a regra de nomes compostos
    }
  }
  