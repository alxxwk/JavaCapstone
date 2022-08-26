
//start of modbtn and modal 
$('document').ready(function() {
	$('.table .modbtn').on('click', function(event) {
		event.preventDefault();

		// this refer to the current element i.e. anchor
		var href = $(this).attr('href');
		$.get(href, function(customers) {
			$('#cid').val(customers.custid);
			$('#cname').val(customers.custname);
			$('#loc').val(customers.location);
			$('#balance').val(customers.balance);
		});

		$('#editModal').modal();

	});

});

$('#editModal').on('shown.bs.modal', function() {
	$('#cname').focus();
	$('#cname').select();
});

//end of edit/mod btn and modal

//start of delbtn and modal 
$('document').ready(function() {
	$('.table .delbtn').on('click', function(event) {
		event.preventDefault();

		// this refer to the current element i.e. anchor
		var href = $(this).attr('href');
		$.get(href, function(customers) {
			$('#cidd').val(customers.custid);
			$('#cnamed').val(customers.custname);
			$('#locd').val(customers.location);
			$('#balanced').val(customers.balance);
		});

		$('#delModal').modal();

	});

});

//end of del btn and modal

//start of deposit/withdraw btn and modal

$('document').ready(function() {
	$('.table .depbtn').on('click', function(event) {
		event.preventDefault();

		// this refer to the current element i.e. anchor
		var href = $(this).attr('href');
		$.get(href, function(customers) {
			$('#cid').val(customers.custid);
			$('#cname').val(customers.custname);
			$('#balance').val(customers.balance);
		});

		$('#depModal').modal();

	});

});

$('document').ready(function() {
	$('.table .withbtn').on('click', function(event) {
		event.preventDefault();

		// this refer to the current element i.e. anchor
		var href = $(this).attr('href');
		$.get(href, function(customers) {
			console.log(customers.custid);
			$('#cidw').val(customers.custid);
			$('#cnamew').val(customers.custname);
			$('#balancew').val(customers.balance);
		});

		$('#withModal').modal();

	});

});

$('#depModal').on('shown.bs.modal', function() {
	$('#deposit').focus();
	$('#deposit').select();
});

$('#withModal').on('shown.bs.modal', function() {
	$('#withdraw').focus();
	$('#withdraw').select();
});

// end of deposit/withdraw btn and modal

//start of transfer modal

$('document').ready(function() {

	$('.table .transbtn').on('click', function(event) {
		event.preventDefault();

		// this refer to the current element i.e. anchor
		var href = $(this).attr('href');
		$.get(href, function(customers) {
			$('#cidt').val(customers.custid);
			$('#cnamet').val(customers.custname);
			$('#balancet').val(customers.balance);
		});

		$('#transModal').modal();

	});
});
//end of transfer modal



// focus elements
$('#addModal').on('shown.bs.modal', function() {
	$('#balance').focus();
});

$('.modal').on('shown.bs.modal', function() {
  $(this).find('[autofocus]').focus();
}); 

