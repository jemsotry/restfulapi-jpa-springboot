// example list
var products = [{
    id: 1,
    name: 'Angular',
    currentPrice: 100
  },
  {
    id: 2,
    name: 'Ember',
    currentPrice: 100
  },
  {
    id: 3,
    name: 'React',
    currentPrice: 100
  }
];

var product = [];

function findProduct(productId) {
  console.log(productId)
  return products[findProductKey(productId)];
};


function axiosTest(productId) {
  return axios.get('/api/v1/products/' + productId).then(response => {
    // returning the data here allows the caller to get it through another .then(...)
    return response.data
  })
}


function getProducto(productId) {
  return axios.get('/api/v1/products/' + productId);
}


var goDelete = Vue.extend({
  template: '#product-list',
  data: function () {
    return {
      products: null,
      searchKey: ''
    };
  },
  created() {
    this.getProducts();
  },
  methods: {
    getProducts() {
      axios.get('/api/v1/products')
        .then((res) => {
          this.products = res.data;
        });
    },
  }
});

function findProductKey(productId) {
  for (var key = 0; key < products.length; key++) {
    if (products[key].id == productId) {
      return key;
    }
  }
};

var List = Vue.extend({
  template: '#product-list',
  data: function () {
    return {
      products: null,
      searchKey: ''
    };
  },
  created() {
    this.getProducts();
  },
  methods: {
    getProducts() {
      axios.get('/api/v1/products')
        .then((res) => {
          this.products = res.data;
        });
      router.go('/');
    },
  }
});


var Product = Vue.extend({
  template: '#product',
  data: function () {
    return {
      product: findProduct(this.$route.params.product_id)
    };
  }
});

var ProductEdit = Vue.extend({
  template: '#product-edit',
  data: function () {
    return {
      product: null,
      error: null
    };
  },
  created() {
    this.getProduct();
  },
  methods: {
    
    getProduct: function () {
      return axios.get('/api/v1/products/' + this.$route.params.product_id)
        .then((res) => {
          this.product = res.data;
        });
    },

    updateProduct: function () {
      var product = this.$get('product');
      // products[findProductKey(product.id)] = {
      //   id: product.id,
      //   name: product.name,
      //   currentPrice: product.currentPrice
      // }; ?name=tama&currentPrice=1&descripti
      console.log(this.product)
      axios.put('/api/v1/products/' 
        + this.$route.params.product_id 
        + '?name=' + product.name 
        + '&currentPrice=' + product.currentPrice 
        + '&description=' + product.description  
        )
        .then(function (response) {
          // do something...
          router.go('/');
        }.bind(this))
        .catch(function (error) {
          console.log(error)
        });
    }
  }
});

var ProductDelete = Vue.extend({
  template: '#product-delete',
  data: function () {
    //return {product: findProduct(this.$route.params.product_id)};
    return {
      product: null
    };
  },
  created() {
    this.getProduct();
  },
  methods: {

    getProduct: function () {
      return axios.get('/api/v1/products/' + this.$route.params.product_id)
        .then((res) => {
          this.product = res.data;
        });
    },

    deleteProduct: function () {
      //products.splice(findProductKey(this.$route.params.product_id), 1);
      axios.delete('/api/v1/products/' + this.$route.params.product_id).then((res) => {
        this.product = res.data;
        router.go('/');
      });
    }
  }
});

var AddProduct = Vue.extend({
  template: '#add-product',
  data: function () {
    return {
      product: {
        name: '',
        currentPrice: ''
      }
    }
  },
  methods: {
    createProduct: function () {
      //var product = this.$get('product');
      // original push array
      // products.push({
      //   id: Math.random().toString().split('.')[1],
      //   name: product.name,
      //   currentPrice: product.currentPrice
      axios.post('/api/v1/products', this.$get('product'))
        .then((response) => {
          console.log('ID: ' + response.data.id)
          router.go('/');
        });
    }
  }
});

var router = new VueRouter();
router.map({
    '/': {
      component: List
    },
    '/product/:product_id': {
      component: Product,
      name: 'product'
    },
    '/add-product': {
      component: AddProduct
    },
    '/product/:product_id/edit': {
      component: ProductEdit,
      name: 'product-edit'
    },
    '/product/:product_id/delete': {
      component: ProductDelete,
      name: 'product-delete'
    }
  })
  .start(Vue.extend({}), '#app');