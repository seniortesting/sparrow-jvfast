const Script2 = {
  installed: false,
  p: Promise.resolve(),
  version: '2.1.0', // grunt will overwrite to match package.json
  loaded: {}, // keys are the scripts that is loading or loaded, values are promises
  install (Vue, options = {}) {
    if (Script2.installed) { return }
    const customAttrs = ['unload']
    // from: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script
    // 'async' and 'defer' don't allow document.write according to:
    // http://www.html5rocks.com/en/tutorials/speed/script-loading/
    // we ignore 'defer' and handle 'async' specially.
    const props = customAttrs.concat(['src', 'type', 'async', 'integrity', 'text', 'crossorigin'])
    Vue.component('script2', {
      props,
      mounted () {
        const parent = this.$el.parentElement
        if (!this.src) {
          Script2.p = Script2.p.then(() => {
            const s = document.createElement('script')
            let h = this.$el.innerHTML
            h = h.replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/&amp;/gi, '&')
            s.type = 'text/javascript'
            s.appendChild(document.createTextNode(h))
            parent.appendChild(s)
            this.$emit('loaded') // any other proper way to do this or emit error?
          })
        } else {
          const opts = _.omitBy(_.pick(this, props), _.isUndefined)
          opts.parent = parent
          // this syntax results in an implicit return
          const load = () => Script2.load(this.src, opts).then(
            () => this.$emit('loaded'),
            err => this.$emit('error', err)
          )
          _.isUndefined(this.async) || this.async === 'false'
            ? Script2.p = Script2.p.then(load) // serialize execution
            : load() // inject immediately
        }
        // see: https://vuejs.org/v2/guide/migration.html#ready-replaced
        this.$nextTick(() => {
          // code that assumes this.$el is in-document
          // NOTE: we could've done this.$el.remove(), but IE sucks, see:
          //       https://github.com/taoeffect/vue-script2/pull/17
          this.$el.parentElement.removeChild(this.$el) // remove dummy template <div>
        })
      },
      destroyed () {
        if (this.unload) {
          new Function(this.unload)() // eslint-disable-line
          delete Script2.loaded[this.src]
        }
      },
      // <slot> is important, see: http://vuejs.org/guide/components.html#Named-Slots
      // template: '<div style="display:none"><slot></slot></div>',
      // NOTE: Instead of using `template` we can use the `render` function like so:
      render (h) { return h('div', { style: 'display:none' }, this.$slots.default) }
    })
    Script2.installed = true
  },
  load (src, opts = { parent: document.head }) {
    if (!Script2.loaded[src]) {
      Script2.loaded[src] = new Promise((resolve, reject) => {
        const s = document.createElement('script')
        // omit the special options that Script2 supports
        _.defaults2(s, _.omit(opts, ['unload', 'parent']), { type: 'text/javascript' })
        // according to: http://www.html5rocks.com/en/tutorials/speed/script-loading/
        // async does not like 'document.write' usage, which we & vue.js make
        // heavy use of based on the SPA style. Also, async can result
        // in code getting executed out of order from how it is inlined on the page.
        s.async = false // therefore set this to false
        s.src = src
        // crossorigin in HTML and crossOrigin in the DOM per HTML spec
        // https://html.spec.whatwg.org/multipage/embedded-content.html#dom-img-crossorigin
        if (opts.crossorigin) {
          s.crossOrigin = opts.crossorigin
        }
        // inspiration from: https://github.com/eldargab/load-script/blob/master/index.js
        // and: https://github.com/ded/script.js/blob/master/src/script.js#L70-L82
        s.onload = () => resolve(src)
        // IE should now support onerror and onload. If necessary, take a look
        // at this to add older IE support: http://stackoverflow.com/a/4845802/1781435
        s.onerror = () => reject(new Error(src))
        opts.parent.appendChild(s)
      })
    }
    return Script2.loaded[src]
  }
}

const _ = {
  isUndefined (x) { return x === undefined },
  pick (o, props) {
    const x = {}
    props.forEach((k) => { x[k] = o[k] })
    return x
  },
  omit (o, props) {
    const x = {}
    Object.keys(o).forEach((k) => { if (!props.includes(k)) { x[k] = o[k] } })
    return x
  },
  omitBy (o, pred) {
    const x = {}
    Object.keys(o).forEach((k) => { if (!pred(o[k])) { x[k] = o[k] } })
    return x
  },
  // custom defaults function suited to our specific purpose
  defaults2 (o, ...sources) {
    sources.forEach((s) => {
      Object.keys(s).forEach((k) => {
        if (_.isUndefined(o[k]) || o[k] === '') { o[k] = s[k] }
      })
    })
  }
}

export default Script2
