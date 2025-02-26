<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Mail\Mailable;
use Illuminate\Mail\Mailables\Content;
use Illuminate\Mail\Mailables\Envelope;
use Illuminate\Queue\SerializesModels;
use Carbon\Carbon;

class GenericoEmail extends Mailable
{
    use Queueable, SerializesModels;
    public $data;


    /**
     * Create a new message instance.
     *
     * @return void
     */
    public function __construct($data)
    {
        $this->data = $data;
        if (!isset($this->data['subject'])) {
            $this->data['subject'] = 'Achei Digital';
        }
    }
    public function build()
    {
        $currentDate = Carbon::now();
        $dataMessage = "Email enviado em: " . $currentDate->format('d/m/Y ') . "às " . $currentDate->format('H:i:s');
        $this->data['emailMessage'] = $dataMessage;

        if (isset($this->data['latitude']) && isset($this->data['longitude'])) {
            $this->data['subject'] = "Qrcode Scaneado";
            return $this->view('emails.emailLatitudeLongitude')
                ->with('data', $this->data)
                ->subject($this->data['subject']);
        } else {
            return $this->view('emails.emailGenerico')
                ->with('data', $this->data)
                ->subject($this->data['subject']);
        }
    }

    /**
     * Get the message envelope.
     *
     * @return \Illuminate\Mail\Mailables\Envelope
     */
    public function envelope()
    {
        return new Envelope(
            subject: $this->data['subject'],
        );
    }

    /**
     * Get the message content definition.
     *
     * @return \Illuminate\Mail\Mailables\Content
     */
    public function content()
    {
        $view = isset($this->data['latitude']) && isset($this->data['longitude']) ? 'emails.emailLatitudeLongitude' : 'emails.emailGenerico';
        return new Content(
            view: $view,
        );
    }

    /**
     * Get the attachments for the message.
     *
     * @return array
     */
    public function attachments()
    {
        return [];
    }
}
