<template>
  <!-- eslint-disable vue/require-component-is -->
  <component v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
// import { isExternal } from '@/util/ValidateUtil'

export default {
  props: {
    to: {
      type: String,
      required: true
    },
    external: {
      type: Boolean,
      required: true
    }
  },
  methods: {
    linkProps (routerName) {
      if (this.external) {
        return {
          is: 'nuxt-link',
          to: { name: 'index-iframe', query: { src: routerName } }
        }
      }
      return {
        is: 'nuxt-link',
        to: { name: routerName }
      }
    }
  }
}
</script>
