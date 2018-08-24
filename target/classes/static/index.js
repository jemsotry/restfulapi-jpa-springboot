var products = [
  {id: 1, name: 'Angular', currentPrice: 100},
  {id: 2, name: 'Ember', currentPrice: 100},
  {id: 3, name: 'React', currentPrice: 100}
];
 const myObj = {
  id:1,
  name: 'Skip',
  currentPrice: 100
};

function findProduct (productId) {
  return products[findProductKey(productId)];
};

function findProductKey (productId) {
  for (var key = 0; key < products.length; key++) {
    if (products[key].id == productId) {
      return key;
    }
  }
};

var List = Vue.extend({
  template: '#product-list',
  // data: function () {
  //   return {products: products, searchKey: ''};
  // }
  data: function () {
      axios.get('/api/v1/products').then(function (response) {
       var lst = []

        
        //lst = JSON.stringify(response.data);
        //console.log(lst)
        for(var key in response.data){
          myObj = JSON.stringify(response.data[key]);
          lst.push(myObj);
        };
        products = lst;
        console.log(products)
      })
      return {products: products, searchKey: ''};
    }
});



var Product = Vue.extend({
  template: '#product',
  data: function () {
    return {product: findProduct(this.$route.params.product_id)};
  }
});

var ProductEdit = Vue.extend({
  template: '#product-edit',
  data: function () {
    return {product: findProduct(this.$route.params.product_id)};
  },
  methods: {
    updateProduct: function () {
      var product = this.$get('product');
      products[findProductKey(product.id)] = {
        id: product.id,
        name: product.name,
        currentPrice: product.currentPrice
      };
      router.go('/');
    }
  }
});

var ProductDelete = Vue.extend({
  template: '#product-delete',
  data: function () {
    return {product: findProduct(this.$route.params.product_id)};
  },
  methods: {
    deleteProduct: function () {
      products.splice(findProductKey(this.$route.params.product_id), 1);
      router.go('/');
    }
  }
});

var AddProduct = Vue.extend({
  template: '#add-product',
  data: function () {
    return {product: {name: '', currentPrice: ''}
    }
  },
  methods: {
    createProduct: function() {
      var product = this.$get('product');
      products.push({
        id: Math.random().toString().split('.')[1],
        name: product.name,
        currentPrice: product.currentPrice
      });
      router.go('/');
    }
  }
});

var router = new VueRouter();
router.map({
  '/': {component: List},
  '/product/:product_id': {component: Product, name: 'product'},
  '/add-product': {component: AddProduct},
  '/product/:product_id/edit': {component: ProductEdit, name: 'product-edit'},
  '/product/:product_id/delete': {component: ProductDelete, name: 'product-delete'}
})
  .start(Vue.extend({}), '#app');