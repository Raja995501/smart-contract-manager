document.addEventListener('DOMContentLoaded', function () {
    console.log("Ram Ram");
  
    const viewContactModel = document.getElementById('view_contact_model');
  
    if (typeof Modal === 'undefined') {
      console.error('Modal is not defined. Make sure the relevant JS library is loaded.');
      return;
    }
  
    const options = {
      placement: 'bottom-right',
      backdrop: 'dynamic',
      backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
      closable: true,
      onHide: () => {
        console.log('modal is hidden');
      },
      onShow: () => {
        console.log('modal is shown');
      },
      onToggle: () => {
        console.log('modal has been toggled');
      }
    };
  
    const instanceOptions = {
      id: "view_contact_model",
      override: true
    };
  
    const modalInstance = new Modal(viewContactModel, options, instanceOptions);
  
    window.openContactModal = async function (contactId) {
      console.log("Contact ID passed to modal:", contactId);

      try {
        const response = await fetch(`http://localhost:8080/api/${contactId}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log(data);
        document.querySelector("#contact_name").innerText = data.name;
        document.querySelector("#contact_email").innerText = data.email;
        document.querySelector("#contact_address").innerText = data.address;
        document.querySelector("#contact_description").innerText = data.description;
        document.querySelector("#contact_phone").innerText = data.phoneNumber;
        document.querySelector("#contact_linkedin").innerText = data.linkedlinLink;
        document.querySelector("#contact_website").innerText = data.websiteLink;
        
        // Set profile picture (assuming data.picture contains the image URL)
        const pictureElement = document.querySelector("#contact_picture");
        pictureElement.src = data.picture;
        pictureElement.alt = `${data.name}'s picture`;
        // Here you can update the modal content with the contact data
        // e.g., document.getElementById('modal-content').innerText = JSON.stringify(data);
        modalInstance.show(); // Show the modal
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
      }
    };
});
