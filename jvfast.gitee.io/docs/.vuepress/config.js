const HighlightDsl = require('./highlight-dsl')
const HighlightRules = require('./highlight-rules')
const base = process.env.NODE_ENV ==='production' ? '/' : '/'
const sidebar= require('./sidebar')
module.exports = {
  title: "JVFast管理框架",
  description: "这是一个创建在jvfast/jvfast仓库的文档管理站点",
  dest: "../jvfast.gitee.io-dist",
  host: "localhost",
  base: base,
  shouldPrefetch: () => false,
  head: [
    ['link', { rel: 'alternate', type: 'application/rss+xml', href: '/rss.xml', title: 'JVFast管理框架' }],
    ['link', { rel: 'stylesheet', href: `/fonts/fonts.css` }],
    ['link', { rel: 'icon', href: `/favicon.ico` }],
    ['link', { rel: 'shortcut icon', href: `/favicon.ico` }],
    ['link', { rel: 'apple-touch-icon', href: `/logo.png` }],
    // ['link', { rel: 'stylesheet', href: `https://fonts.googleapis.com/css?family=Open+Sans:300,400` }],
    ['meta', { property: 'og:type', content: 'website' }],
    ['script', {
    id: 'cookieinfo', src: 'https://cookieinfoscript.com/js/cookieinfo.min.js', 'data-message': "我们使用cookies来提高用户体验,继续浏览网站表示同意我们使用cookies.", 'data-linkmsg': '了解更多', 'data-bg': "#645862" ,'data-fg': "#FFFFFF", 'data-link': "#F1D600" ,'data-cookie': "CookieInfoScript", 'data-text-align': "left", 'data-close-text':"知道了!" }]
  ],
  markdown: {
    config: md => {
      md.options.linkify = true
      const highlight = md.options.highlight
      md.options.highlight = (str, lang) => {
        /* Simple heuristics to detect rules & other openHAB DSL code snippets and override the language */
        if (str.match(/\b(?:Color|Contact|Dimmer|Group|Number|Player|Rollershutter|Switch|Location|Frame|Default|Text|Group|Selection|Setpoint|Slider|Colorpicker|Chart|Webview|Mapview|Image|Video|Item|Thing|Bridge|Time|Type|Sitemap|sitemap)\b/)) {
          lang = 'dsl'
        }
        if (str.match(/\b(?:String|DateTime)\b/) && lang !== 'java' && lang !== 'xml') {
          lang = 'dsl'
        }
        if ((str.match(/\brule\b/) && str.match(/\bwhen\b/) && str.match(/\bthen\b/) && str.match(/\bend\b/)) ||
            str.match(/received update/) || str.match(/changed.*(?:from|to)/) || str.match(/Channel.*triggered/) ||
            str.match(/\bval\b/) || str.match(/\bvar\b/) /* <-- dangerous! */) {

          if (lang !== 'nginx' && lang !== 'shell') lang = 'rules'
        }
        if (lang === 'shell' || lang === 'sh' || lang === 'shell_session') lang = 'bash'
        if (lang === 'conf') lang = 'dsl'
        if (lang === 'JSON') lang = 'json'
        // if (lang === 'xtend' || lang === 'text' || !lang) {
        //   console.log('Cannot determine language of code: ' + lang)
        //   console.log(str)
        // }

        if (!Prism.languages.dsl || !Prism.languages.rules) {
          Prism.languages.dsl = HighlightDsl
          Prism.languages.rules = HighlightRules
        }

        return highlight(str, lang)
      }
    }
  },
  serviceWorker: false,
  extraWatchFiles: [
    '.vuepress/config.js',
    '.vuepress/sidebar.js'
  ],
  plugins: [
    ['@vuepress/search', {
      search: true,
      searchMaxSuggestions: 10
    }],
    ['vuepress-plugin-seo', {
      siteTitle: (_, $site) => $site.title,
      title: $page => $page.title,
      description: $page => $page.frontmatter.description,
      author: (_, $site) => $site.themeConfig.author,
      tags: $page => $page.frontmatter.tags,
      twitterCard: _ => 'summary_large_image',
      type: $page => ['articles', 'posts', 'blog'].some(folder => $page.regularPath.startsWith('/' + folder)) ? 'article' : 'website',
      url: (_, $site, path) => ($site.themeConfig.domain || '') + path,
      image: ($page, $site) => $page.frontmatter.image && (($site.themeConfig.domain || '') + $page.frontmatter.image),
      publishedAt: $page => $page.frontmatter.date && new Date($page.frontmatter.date),
      modifiedAt: $page => $page.lastUpdated && new Date($page.lastUpdated)
    }],
    ['@vuepress/google-analytics',{
      ga: 'UA-122219517-1'
    }],
    ['vuepress-plugin-sitemap', {
      hostname: 'https://doc.pingbook.top'
    }],
    ['vuepress-plugin-rss-support', {
      site_url: 'https://doc.pingbook.top',
      filter: page => /^\/201.+/.test(page.path),
      copyright: '2019 JVFast管理框架',
      count: 60
    }],
    ['vuepress-plugin-zooming', {
      // selector for images that you want to be zoomable
      // default: '.content img'
      selector: '.content img',
      // make imgaes zoomable with delay after entering a page
      // default: 500
      delay: 500,
      // options of zooming
      // default: {}
      options: {
        bgColor: 'black',
        zIndex: 10000,
      },
    }],
    ['@vuepress/back-to-top', true],
    ['@vssue/vuepress-plugin-vssue', {
      locale: 'zh',
      platform: 'gitee',
      owner: 'jvfast',
      repo: 'jvfast',
      state: 'Vssue',
      prefix: '[JVFast管理框架]',
      labels: ['留言'],
      clientId: '726edd4be82fb53e7ba2973f714f731ea9833ecb38d70d5b59b181b31876f6fd',
      clientSecret: '1bd5e17f4fa1040d34a3ed78a0390a1ea14ea7a948f34cf2214e06ee20210022'
    }],
  ],
  themeConfig: {
    // repo: 'alterhu2020/vrpano-docs',
    docsDir: 'docs',
    editLinks: true,
    editLinkText: '发现错误？想参与编辑？在 Gitee 上编辑此页',
    lastUpdated: '上次更新',
    nav: [
      {
        text: '使用指南',
        link: '/'
      },
      {
        text: '版本更新',
        link: '/references/CHANGELOG/'
      },
      {
        text: '建议帮助',
        link: '/other/comments/'
      },
      {
        text: '演示',
        link: 'https://jvfast.pingbook.top/'
      },
      {
        text: '博客',
        link: 'https://code.pingbook.top/blog/'
      }
    ],
    sidebar:sidebar
  }
};
