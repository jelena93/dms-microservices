var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedCompanyId = null;

$('#roles').change(function () {
    var roles = $(this).val();
    if (roles === null || roles.length === 1 && roles[0] === "ADMIN") {
        $("#companySection").hide();
        $('label[for="company"]').eq(1).hide();
    } else {
        $("#companySection").show();
    }
});

$('#table-companies-set-user').on('click', '.clickable-row', function (event) {
    if ($(this).hasClass('active')) {
        $(this).removeClass('active');
        selectedCompanyId = null;
        $("#company").val(null);
        $('label[for="company"]').eq(1).show();
    } else {
        selectedCompanyId = $(this).attr('id');
        $(this).addClass('active').siblings().removeClass('active');
        $("#company").val(selectedCompanyId);
        $('label[for="company"]').eq(1).hide();
    }
});

function search(query) {
    $.ajax({
        type: "GET",
        url: "/api/company/search",
        data: {query: query},
        dataType: 'json',
        success: function (data) {
            $("#table-companies-set-user tbody").html('');
            selectedCompanyId = null;
            for (var i = 0; i < data.length; i++) {
                $('#table-companies-set-user tbody').append('<tr class="clickable-row" id="' + data[i].id + '"><td>' + data[i].id + '</td><td>' + data[i].name + '</td><td>' +
                        data[i].pib + '</td><td>'
                        + data[i].identificationNumber + '</td><td>' +
                        data[i].headquarters
                        + '</td></tr>');
            }
        },
        error: function (request) {
            console.log(request);
            showErrorMessage(request.responseText);
        }
    });
}

function addUser() {
    if ($("#register_form").valid()) {
        $.ajax({
            type: "POST",
            url: "/api/company/user",
            contentType: "application/json",
            dataType: 'json',
            // beforeSend: function (request) {
            //     request.setRequestHeader(header, token);
            // },
            data: JSON.stringify({
                name: $("#name").val(),
                surname: $("#surname").val(),
                username: $("#username").val(),
                password: $("#password").val(),
                roles: $("#roles").val(),
                companyId: selectedCompanyId
            }),
            success: function (data) {
                window.location = "/user/search";
            },
            error: function (request) {
                console.log(request);
                showErrorMessage(request.responseText);
            }
        });
    }

}
