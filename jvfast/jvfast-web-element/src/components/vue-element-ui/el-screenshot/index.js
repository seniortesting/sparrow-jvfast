
export default (doc, option = {}) => {
  if (!window.html2canvas) {
    console.logs('Missing html2canvas package')
    return
  }
  return window.html2canvas(doc, option)
}
