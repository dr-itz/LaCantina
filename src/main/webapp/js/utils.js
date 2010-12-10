function formSubmit(form)
{
	for (var i = 1; i < arguments.length; i += 2) {
		if (i + 1 < arguments.length)
			document.getElementById(arguments[i]).value = arguments[i + 1];
	}
	document.getElementById(form).submit();
}

function pfConfirm(form, act)
{
	var ok = confirm("Are you sure?")
	if (ok)
		formSubmit(form, 'pAction', act);
}

function pfDelete(form)
{
	pfConfirm(form, 'del');
}